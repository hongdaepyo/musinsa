# 무신사 과제

### 빌드 방법
```shell
./gradlew clean build
```

### 실행 방법
```shell
./gradlew bootRun
```

### 구현1. 카테고리 별 최저가격 브랜드와 상품가격, 총액을 조회하는 API
- GET /v1/products/lowest
- Response Body
    ```json
    {
        "code": "SUCCESS",
        "message": "Success",
        "data": {
            "products": [
                {
                    "category": "TOP",
                    "name": "테스트 상의",
                    "brandName": "A",
                    "price": 1000
                },
                {
                    "category": "OUTER",
                    "name": "테스트 아우터",
                    "brandName": "B",
                    "price": 1000
                }
            ],
            "totalAmount": 2000
        }
    }
  ```

### 구현2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
- GET /v1/products/lowest/brand
- Response Body
    ```json
    {
        "code": "SUCCESS",
        "message": "Success",
        "data": {
            "lowestPrice": {
                "brandName": "D",
                "categories": [
                    {"categoryName": "상의", "name": "상품1", "price": 10100},
                    {"categoryName": "아우터", "name": "상품2", "price": 5100},
                    {"categoryName": "바지", "name": "상품3", "price": 3000},
                    {"categoryName": "스니커즈", "name": "상품4", "price": 9500},
                    {"categoryName": "가방", "name": "상품5", "price": 2500},
                    {"categoryName": "모자", "name": "상품6", "price": 1500},
                    {"categoryName": "양말", "name": "상품7", "price": 2400},
                    {"categoryName": "악세서리", "name": "상품8", "price": 2000}
                ],
                "totalAmount": 36100
            } 
        }
    }
  ```

### 구현3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
- GET /v1/products/search-by-category
- Request Param
  - name: 카테고리명
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "lowestPrice": {
              "categoryName": "상의",
              "lowestPrice": [
                  {
                      "brandName": "C",
                      "price": 1000
                  }
              ],
              "highestPrice": [
                  {
                      "brandName": "I",
                      "price": 11400
                  }
              ],
              "totalAmount": 36100
          }
      }
  }
  ```

### 구현4. 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API
- 브랜드 추가
- POST /v1/brands
- Request Body
  ```json
  {
      "name": "브랜드 이름"
  }
  ```
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "id": 1,
          "name": "브랜드 이름"
      }
  }
  ```

- 브랜드 수정
- PUT /v1/brands/{id}
- Request Body
  ```json
  {
      "name": "브랜드명"
  }
  ```
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "id": 1,
          "status": "SUCCESS"
      }
  }
  ```

- 브랜드 삭제
- DELETE /v1/brands/{id}
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "id": 1,
          "status": "SUCCESS"
      }
  }
  ```
---
- 상품 추가
- POST /v1/products
- Request Body
  ```json
  {
      "name": "상품명",
      "category": "TOP",
      "price": 1000,
      "brandId": 1
  }
  ```
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "id": 1,
          "name": "상품명"
      }
  }
  ```

- 상품 수정
- PUT /v1/products/{id}
- Request Body
  ```json
  {
      "name": "상품명",
      "category": "TOP",
      "price": 1000
  }
  ```
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "id": 1,
          "status": "SUCCESS"
      }
  }
  ```

- 상품 삭제
- DELETE /v1/products/{id}
- Response Body
  ```json
  {
      "code": "SUCCESS",
      "message": "Success",
      "data": {
          "id": 1,
          "status": "SUCCESS"
      }
  }
  ```
