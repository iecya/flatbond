(ns flatbond.config)

(def debug?
  ^boolean goog.DEBUG)

(def rent-ranges
  {:weekly {:min 25 :max 2000}
   :monthly {:min 110 :max 8660}})
