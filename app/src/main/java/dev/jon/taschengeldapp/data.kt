package dev.jon.taschengeldapp

var nameUser = "";
var currencyUser = "";
var childs = mutableListOf<String>()
// money is given at the first of the month/week at cet time

var balancesChilds = mutableListOf<Double>();
var idsChilds = mutableListOf<String>();
var namesChilds = mutableListOf<String>();

var datesChild = mutableListOf<String>();
var infosChild = mutableListOf<String>();
var transactionsizeChild = mutableListOf<Double>();

var uidd= ""


data class Child(
    var idChild: String,
    var nameChild: String,
    var balanceChild: Double

)

data class AccountChild(
        var info:String,
        var date:String,
        var amount:Double
)