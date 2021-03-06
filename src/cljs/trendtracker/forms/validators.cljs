(ns trendtracker.forms.validators)

(def email-regex #"^([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x22([^\x0d\x22\x5c\x80-\xff]|\x5c[\x00-\x7f])*\x22))*\x40([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d)(\x2e([^\x00-\x20\x22\x28\x29\x2c\x2e\x3a-\x3c\x3e\x40\x5b-\x5d\x7f-\xff]+|\x5b([^\x0d\x5b-\x5d\x80-\xff]|\x5c[\x00-\x7f])*\x5d))*$")

(def url-regex #"https?://(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)")

(defn zero-count? [v]
  (if (satisfies? ICounted v)
    (zero? (count v))
    false))

(defn not-empty? [v _ _]
  (let [v (js->clj v)]
    (cond
      (nil? v) false
      (= "" v) false
      (zero-count? v) false
      :else true)))

(defn url? [v _ _]
  (if (or (nil? v) (empty? v))
    true
    (not (nil? (re-matches url-regex (str v))))))

(defn email? [v _ _]
  (not (nil? (re-matches email-regex (str v)))))

(defn numeric? [v _ _]
  (if (nil? v)
    true
    (re-matches #"^\d+$" v)))

(defn bool? [v _ _]
  (if (nil? v)
    true
    (or (= true v) (= false v))))

(defn ok-password? [v _ _]
  (if (seq v)
    (< 7 (count v))
    true))

(def types
  {:not-empty {:message "공백이면 안됩니다."
               :validator not-empty?}
   :bool {:message "True 또는 False를 선택하셔야 합니다."
          :validator bool?}
   :url {:message "정상적인 URL이 아닙니다."
         :validator url?}
   :email {:message "정상적인 Email이 아닙니다."
           :validator email?}
   :email-confirmation {:message "Email 주소가 일치하지 않습니다."
                        :validator (fn [_ data _]
                                     (let [email (:email data)
                                           email-confirmation (:email-confirmation data)]
                                       (if (some nil? [email email-confirmation])
                                         true
                                         (= email email-confirmation))))}
   :ok-password {:message "Password must have at least 8 characters"
                 :validator ok-password?}
   :numeric {:message "Value is not a number"
             :validator numeric?}})

(defn get-validator-message [type]
  (get-in types [type :message]))

(defn to-validator
  "Helper function that extracts the validator definitions."
  [validations config]
  (reduce-kv (fn [m attr v]
               (assoc m attr
                      (map (fn [k] [k (get-in validations [k :validator])]) v))) {} config))
