(ns shopping-chart.test.core
  (:use [midje.sweet])
  (:use [shopping-chart.core]))

(def cart-lines [(map->QuantityProductCartLine :name "Loaf of bread"
                                               :quantity 1
                                               :offer-amount 10
                                               :unit-price 1)
                 (->QuantityProductCartLine :name "Noodles"
                                            :quantity 1
                                            :offer-amount 10
                                            :unit-price 0.5)
                 
                 (->QuantityProductCartLine :name "Soup cans"
                                            :quantity 1
                                            :offer-amount 10
                                            :unit-price 2)
                 (->WeightProductCartLine "Apples" 2 1)])

(fact (price (->QuantityProductCartLine "Loaf of bread" 1))
  => 1)

(fact (receipt-line (->QuantityProductCartLine "Loaf of bread" 1))
  => "Loaf of bread 1")

(fact (total cart-lines) => 5.5)

(fact (receipt-lines cart-lines) =>
  '("Loaf of bread 1"
    "Noodles 0.5"
    "Soup cans 2"
    "Apples 2$ / 1 pounds"
    "Total 5.5"))
