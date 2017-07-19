const Pusher = require('pusher');
const request = require('sync-request');

var pusher = new Pusher({
    appId: '354052',
    key: 'a446158132cfaa3ed747',
    secret: '6f6ddfd0127e04d23cbb',
    encrypted: true
});

let graph_url = 'https://bitcoincharts.com/charts/chart.png?width=940&m=bitstampUSD&SubmitButton=Draw&r=2&i=15-min&c=0&s=&e=&Prev=&Next=&t=M&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=1&ps=0&l=0&p=0&'
let graph_url_minute = 'https://bitcoincharts.com/charts/chart.png?width=940&m=bitstampUSD&SubmitButton=Draw&r=1&i=1-min&c=0&s=&e=&Prev=&Next=&t=M&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=1&ps=0&l=0&p=0&';

var cron = require('node-cron');

let counter = 0;

const updatePrice = () => {
     let btcprice = JSON.parse(request('GET', 'https://www.bitstamp.net/api/v2/ticker_hour/btcusd/').getBody('utf8'));
    

    let currentPrice = btcprice.last as number;
    let openPrice = btcprice.open as number;
    let currencyPair = "BTCUSD";

    counter += 1;

    pusher.notify(['bitcoin'], {
        fcm: {
            data: {
                graph_url: graph_url_minute,
                currentPrice: currentPrice,
                openPrice: openPrice,
                currencyPair: currencyPair,
                counter: counter
            }
        }
    });

    console.log(
        {
            graph_url: graph_url_minute,
            currentPrice: currentPrice,
            openPrice: openPrice,
            currencyPair: currencyPair,
            counter: counter
        });
}


var task = cron.schedule('* * * * *', () => {
    updatePrice();
});

updatePrice();

