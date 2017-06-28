#! /bin/bash
get() {
  echo "Starting..."
  curl -H "Content-Type: application/json" -X POST -d '{"orderId" : "123","productCode" : "Prod1","qty" : "1"}' http://localhost:8081/processOrder
  echo "Finish"
}



for i in `seq 1 100`;
  do
    get
  done
