package dev.jon.taschengeldapp

var nameUser = "";
var currencyUser = "";
var timezoneUser = "";

var balancesChilds = mutableListOf<Double>();
var idsChilds = mutableListOf<String>();
var namesChilds = mutableListOf<String>();



data class Child(
    var idChild: String,
    var nameChild: String,
    var balanceChild: Double

)