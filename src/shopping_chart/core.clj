(ns shopping-chart.core)

(defn cost [products]
  (reduce + (map :price products)))


(defn receipt [products]
  {:products products
   :total (cost products)})

