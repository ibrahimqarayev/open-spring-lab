# 🛒 E-commerce Backend API

This project is part of **OpenSpringLab**.

The goal of this project is to **learn Spring Boot by building a simple e-commerce backend together**,
step by step, from beginner to more advanced concepts.

This is a **learning-focused project**, not a production system.

---

## 🎯 Project Goals

- Build a simple and clean backend API
- Practice Spring Boot fundamentals
- Learn how to work with GitHub (issues, branches, PRs)
- Collaborate with developers of different experience levels

---

## 🧑‍💻 Who Is This For?

- Beginners learning Spring Boot
- Developers who want hands-on backend experience
- Anyone who wants to practice teamwork using GitHub

No pressure.  
No deadlines.  
Learning by doing.

---

## 🛠️ Tech Stack (Current)

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

More tools (Security, Docker, etc.) will be added in later stages.

---

## ▶️ How to Run the Project

### Prerequisites
- Java 17+
- Maven

### Run locally

```bash
cd ecommerce
mvn spring-boot:run
```

🔌 API Endpoints

Categories

Method	 |   Endpoint	               Description	                   Success Status
         |
GET	     |   /categories	           Get all categories	              200 OK
GET	     |   /categories/{id}	       Get a category by ID	              200 OK
POST	 |   /categories	           Create a new category	        201 Created
PUT	     |   /categories/{id}	       Update an existing category	      200 OK
DELETE	 |   /categories/{id}          Delete a category	           204 No Content

Create a Category

Request

POST /categories
{
  "name": "Electronics",
  "description": "Electronic products"
}

Response

{
  "id": 1,
  "name": "Electronics",
  "description": "Electronic products"
}

Example using curl

curl -X POST http://localhost:8080/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Electronics",
    "description": "Electronic products"
  }'
Get All Categories
curl http://localhost:8080/categories
Get a Category by ID
curl http://localhost:8080/categories/1
Update a Category
PUT /categories/{id}
{
  "name": "Updated Electronics",
  "description": "Updated category description"
}

Example:

curl -X PUT http://localhost:8080/categories/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Electronics",
    "description": "Updated category description"
  }'
Delete a Category
curl -X DELETE http://localhost:8080/categories/1
Products
Method	Endpoint	Description	Success Status
GET	/products	Get all products	200 OK
GET	/products/{id}	Get a product by ID	200 OK
GET	/products?categoryId={id}	Get products by category ID	200 OK
POST	/products	Create a new product	201 Created
PUT	/products/{id}	Update an existing product	200 OK
DELETE	/products/{id}	Delete a product	204 No Content
Create a Product

Request

POST /products
{
  "name": "Laptop",
  "description": "14-inch laptop",
  "price": 799.99,
  "sku": "LAPTOP-001",
  "stockQuantity": 10,
  "categoryId": 1
}

Response

{
  "id": 1,
  "name": "Laptop",
  "description": "14-inch laptop",
  "price": 799.99,
  "sku": "LAPTOP-001",
  "stockQuantity": 10,
  "categoryId": 1
}

Example using curl

curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "14-inch laptop",
    "price": 799.99,
    "sku": "LAPTOP-001",
    "stockQuantity": 10,
    "categoryId": 1
  }'
Get All Products
curl http://localhost:8080/products
Get a Product by ID
curl http://localhost:8080/products/1
Get Products by Category

The categoryId query parameter can be used to return products belonging to a specific category.

curl "http://localhost:8080/products?categoryId=1"
Update a Product
PUT /products/{id}
{
  "name": "Updated Laptop",
  "description": "Updated laptop description",
  "price": 899.99,
  "sku": "LAPTOP-001",
  "stockQuantity": 15,
  "categoryId": 1
}

Example:

curl -X PUT http://localhost:8080/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Laptop",
    "description": "Updated laptop description",
    "price": 899.99,
    "sku": "LAPTOP-001",
    "stockQuantity": 15,
    "categoryId": 1
  }'
Delete a Product
curl -X DELETE http://localhost:8080/products/1

## 🗺️ Next Steps

- Check GitHub Issues
- Pick one beginner-friendly task
- Create a branch and open a Pull Request

Small contributions are welcome 🙂
