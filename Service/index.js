const Pusher = require('pusher');
const request = require('sync-request');

var pusher = new Pusher({
    appId: '[PUSHER_APP_ID]',
    key: '[PUSHER_KEY]',
    secret: '[PUSHER_SECRET]',
    encrypted: true
});

let graph_url = 'https://bitcoincharts.com/charts/chart.png?width=940&m=bitstampUSD&SubmitButton=Draw&r=2&i=15-min&c=0&s=&e=&Prev=&Next=&t=M&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=1&ps=0&l=0&p=0&'

var cron = require('node-cron');

let counter = 0;

const updatePrice = () => {
    let btcprice = JSON.parse(request('GET', 'https://www.bitstamp.net/api/v2/ticker_hour/btcusd/').getBody('utf8'));
    
    let currentPrice = btcprice.last;
    let openPrice = btcprice.open;
    let currencyPair = "BTCUSD";
    
    counter += 1;

    let payload = { 
        graphUrl: graph_url,
        currentPrice: currentPrice,
        openPrice: openPrice,
        currencyPair: currencyPair,
        counter: counter    
    }
    
    pusher.notify(['bitcoin'], {
        fcm: {
            data: payload
        }
    });
    
    console.log(payload);
    }
    
    var task = cron.schedule('*/15 * * * *', () => {
        updatePrice();
    });
    
    updatePrice();
    