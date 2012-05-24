(ns shopping-chart.test.core
  (:use [midje.sweet])
  (:use [shopping-chart.core]))

(def cart-lines [(map->QuantityProductCartLine {:name "Loaf of bread"
                                                :quantity 1
                                                :unit-price 1})

                 (map->QuantityProductCartLine {:name "Noodles"
                                                :quantity 1
                                                :unit-price 0.5})

                 (map->QuantityProductCartLineWithOffer {:name "Soup cans"
                                                         :quantity 5
                                                         :offer-amount 4
                                                         :unit-price 2})

                 (map->WeightProductCartLine {:name "Apples"
                                              :price-by-weight 2
                                              :weight 1})])


(fact (price (map->QuantityProductCartLineWithOffer {:name "Loaf of bread"
                                                     :quantity 3
                                                     :offer-amount 2
                                                     :unit-price 1}))
  => 2.0)

(fact (reduced-price 3 2 1) => 2)
(fact (reduced-price 6 2 1) => 4)
(fact (reduced-price 1 10 1) => 1)
(fact (reduced-price 2 2 1) => 2)
(fact (reduced-price 3 10 3) => (* 3 3))

(fact (receipt-line (map->QuantityProductCartLine {:name "Loaf of bread"
                                                   :quantity 1
                                                   :unit-price 1}))
  => "Loaf of bread $1.0 / 1 pieces")

(fact (total cart-lines) => 11.5)

(fact (receipt-lines cart-lines) =>
  '("Loaf of bread $1.0 / 1 pieces"
    "Noodles $0.5 / 1 pieces"
    "Soup cans $8.0 / 5 pieces (buy 4 get 1 for free)"
    "Apples $2.0 / 1 pounds"
    "Total 11.5"))
