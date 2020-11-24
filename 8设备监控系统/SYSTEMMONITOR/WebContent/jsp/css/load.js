/**
 * 装载站点数据
 */
function loadStations(xmlobj,stationForm) {
    var stationobj = document.all(stationForm);

    stationobj.options.length = 0;
    var file = "../xml/stations.xml";

    xmlobj.async = false;
    xmlobj.load(file);
    var stations = xmlobj.selectNodes("Stations/Station");
    var idx,name;

    for(idx = 0; idx < stations.length; idx ++) {
        name = stations[idx].getAttribute("name");
        stationobj.options.length++;
        stationobj.options[stationobj.options.length - 1].value = name;
        stationobj.options[stationobj.options.length - 1].text = name;
    }
}

/**
 * 装载交易类型数据
 */
function loadPayments(xmlobj,paymentForm) {
    var paymentobj = document.all(paymentForm);

    paymentobj.options.length = 0;
    var file = "../xml/payments.xml";

    xmlobj.async = false;
    xmlobj.load(file);
    var payments = xmlobj.selectNodes("Payments/Payment");
    var idx,name;

    for(idx = 0; idx < payments.length; idx ++) {
        name = payments[idx].getAttribute("name");
        paymentobj.options.length++;
        paymentobj.options[paymentobj.options.length - 1].value = name;
        paymentobj.options[paymentobj.options.length - 1].text = name;
    }
}


/**
 * 装载支付方式数据
 */
function loadTrades(xmlobj,tradeForm) {
    var tradeobj = document.all(tradeForm);

    tradeobj.options.length = 0;
    var file = "../xml/trades.xml";

    xmlobj.async = false;
    xmlobj.load(file);
    var trades = xmlobj.selectNodes("Trades/Trade");
    var idx,name;

    for(idx = 0; idx < trades.length; idx ++) {
        name = trades[idx].getAttribute("name");
        tradeobj.options.length++;
        tradeobj.options[tradeobj.options.length - 1].value = name;
        tradeobj.options[tradeobj.options.length - 1].text = name;
    }
}

/**
 * 装载运营商数据
 */
function loadBuilders(xmlobj,builderForm) {
    var builderobj = document.all(builderForm);

    builderobj.options.length = 0;
    var file = "../xml/builders.xml";

    xmlobj.async = false;
    xmlobj.load(file);
    var builders = xmlobj.selectNodes("Builders/Builder");
    var idx,name;

    for(idx = 0; idx < builders.length; idx ++) {
        name = builders[idx].getAttribute("name");
        builderobj.options.length++;
        builderobj.options[builderobj.options.length - 1].value = name;
        builderobj.options[builderobj.options.length - 1].text = name;
    }
}


function openCalenderDialog(){
    window.showModalDialog('calenderD.html','calender','dialogWidth:290px;dialogHeight:435px;center:yes;resizable:no;status:no');
}
