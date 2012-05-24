(ns shopping-chart.core)

(defprotocol CartLine
  (price [cart-line])
  (receipt-line [cart-line]))


(defn reduced-price [quantity offer-amount unit-price]
  (* (- quantity
        (int (/ quantity
                (+ 1
                   offer-amount))))
     unit-price))

(defrecord QuantityProductCartLineWithOffer [name quantity offer-amount unit-price]
  CartLine

  (price [quantity-product-cart-line] (let [{:keys [quantity offer-amount unit-price]} quantity-product-cart-line]
                                        (float (reduced-price quantity offer-amount unit-price))))

  (receipt-line [quantity-product-cart-line] (let [{:keys [name quantity offer-amount unit-price]} quantity-product-cart-line]
                                               (str name
                                                    " $"
                                                    (price quantity-product-cart-line)
                                                    " / " quantity " pieces"
                                                    " (buy " offer-amount " get 1 for free)"))))


(defrecord QuantityProductCartLine [name quantity unit-price]
  CartLine

  (price [quantity-product-cart-line] (let [{:keys [quantity unit-price]} quantity-product-cart-line]
                                        (float (* quantity unit-price))))

  (receipt-line [quantity-product-cart-line] (let [{:keys [name quantity]} quantity-product-cart-line]
                                               (str name
                                                    " $"
                                                    (price quantity-product-cart-line)
                                                    " / " quantity " pieces"))))


(defrecord WeightProductCartLine [name price-by-weight weight]
  CartLine

  (price [weight-product-cart-line] (* (:price-by-weight weight-product-cart-line)
                                       (:weight weight-product-cart-line)))

  (receipt-line [weight-product-cart-line] (str (:name weight-product-cart-line)
                                                " $"
                                                (float (price weight-product-cart-line))
                                                " / "
                                                (:weight weight-product-cart-line)
                                                " pounds")))


(defn total [cart-lines]
  (reduce + (map price cart-lines)))

(defn receipt-lines [cart-lines]
  (concat (map receipt-line
               cart-lines)
          [(str "Total " (total cart-lines))]))
