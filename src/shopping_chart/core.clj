(ns shopping-chart.core)

(defprotocol CartLine
  (price [cart-line])
  (receipt-line [cart-line]))

(defrecord QuantityProductCartLine [name quantity offer-amount unit-price]
  CartLine

  (price [quantity-product-cart-line] (let [{:keys [quantity offer-amount unit-price]} quantity-product-cart-line]
                                        (* quantity
                                           unit-price)))

  (receipt-line [quantity-product-cart-line] (str (:name quantity-product-cart-line)
                                                  " "
                                                  (:price quantity-product-cart-line))))

(defrecord WeightProductCartLine [name price-by-weight weight]
  CartLine

  (price [weight-product-cart-line] (* (:price-by-weight weight-product-cart-line)
                                       (:weight weight-product-cart-line)))

  (receipt-line [weight-product-cart-line] (str (:name weight-product-cart-line)
                                                " "
                                                (price weight-product-cart-line)
                                                "$ / "
                                                (:weight weight-product-cart-line)
                                                " pounds")))
(defn total [cart-lines]
  (reduce + (map price cart-lines)))

(defn receipt-lines [cart-lines]
  (concat (map receipt-line
               cart-lines)
          [(str "Total " (total cart-lines))]))
