package com.example.firestore

import kotlin.reflect.typeOf

fun main() {
//    var arr = arrayOf(20, 50, 52, 35)
//    var countEven = 0
//    var countOdd = 0
//    arr.forEach { a ->
//        if (a % 2 == 0) {
//            countEven++
//            println("$a is Even number")
//        } else {
//            countOdd++
//            println("$a is Odd number")
//        }
//
//    }
//    println("$countEven $countOdd")
    var arr = arrayOf(20, 50, 52, 35,31)
    arr.forEach { a ->                      /* 20/1==0,20/2==0,20/3==2*/
        var p =0
        for(b in 1..a){
            if(a%b==0){
                p++
            }
        }
        if(p<=2){
           println("$a is prime")
        }else{
            println("$a is Not prime")
        }
    }


}