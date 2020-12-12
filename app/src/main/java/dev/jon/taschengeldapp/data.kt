package dev.jon.taschengeldapp

var nameUser = "";
var currencyUser = "";
var timezoneUser = "";

var balancesChilds = mutableListOf<Double>();
var idsChilds = mutableListOf<String>();
var namesChilds = mutableListOf<String>();

var settingChild = "";
var moneyChild = 0.0;
var nameChild = "";
var datesChild = mutableListOf<String>();
var infosChild = mutableListOf<String>();
var transactionsizeChild = mutableListOf<Double>();

data class Child(
    var idChild: String,
    var nameChild: String

)