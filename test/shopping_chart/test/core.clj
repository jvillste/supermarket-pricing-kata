(ns shopping-chart.test.core
  (:use [midje.sweet])
  (:use [shopping-chart.core]))


(def products [{:name "Loaf of bread" :price 1}
               {:name "Noodles" :price 0.5}
               {:name "Soup cans" :price 2}])

(fact (cost products) => 3.5)

(fact (receipt products)=>
  {:products [{:name "Loaf of bread" :price 1}
              {:name "Noodles" :price 0.5}
              {:name "Soup cans" :price 2}]
   :total 3.5})

