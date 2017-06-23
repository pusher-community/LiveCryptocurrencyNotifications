const Pusher = require('pusher');
const request = require('sync-request');

var pusher = new Pusher({

});

let graph_url = 'https://bitcoincharts.com/charts/chart.png?width=940&m=bitstampUSD&SubmitButton=Draw&r=2&i=15-min&c=0&s=&e=&Prev=&Next=&t=M&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=1&ps=0&l=0&p=0&'
let graph_url_minute = 'https://bitcoincharts.com/charts/chart.png?width=940&m=bitstampUSD&SubmitButton=Draw&r=1&i=1-min&c=0&s=&e=&Prev=&Next=&t=M&b=&a1=&m1=10&a2=&m2=25&x=0&i1=&i2=&i3=&i4=&v=1&cv=1&ps=0&l=0&p=0&';

var cron = require('node-cron');

let counter = 0;

const updatePrice = () => {
     let btcprice = JSON.parse(request('GET', 'https://www.bitstamp.net/api/v2/ticker_hour/btcusd/').getBody('utf8'));
    

    let currentPrice = btcprice.last as number;
    let highPrice = btcprice.high as number;
    let lowPrice = btcprice.low as number;
    let avgPrice = btcprice.vwap as number;
    let openPrice = btcprice.open as number;

    //calculate trend
    let trend = 0;
    let difference = currentPrice/openPrice;

    if( difference  <= 0.8){
        trend = -3;
    }
    else if( difference <= 0.95){
        trend = -2;
    }
    else if( difference < 1.0){
        trend = -1;
    }
    else if(difference == 1){
        trend = 0;
    }
    else if( difference <= 1.05 ){
        trend = 1;
    }
    else if( difference <= 1.20 ){
        trend = 2;
    }
    else {
        trend = 3
    }

    counter += 1;

    pusher.notify(['bitcoin'], {
        fcm: {
            data: {
                graph_url: graph_url,
                price: currentPrice,
                open: openPrice,
                trend: trend,
                counter: counter,
                lowPrice: lowPrice,
                highPrice: highPrice
            }
        }
    });

    console.log(
        {
            graph_url: graph_url,
                price: currentPrice,
                open: openPrice,
                trend: trend,
                counter: counter,
                lowPrice: lowPrice,
                highPrice: highPrice
        });
}


var task = cron.schedule('*/15 * * * *', () => {
    updatePrice();
});

updatePrice();

