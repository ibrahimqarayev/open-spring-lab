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
---
## 📍 API Endpoints

| Endpoint | Method | Description |
|---------|--------|-------------|
| `/categories` | GET | Retrieve a list of all categories. |
| `/categories/{id}` | GET | Retrieve a specific category by its ID. |
| `/categories` | POST | Create a new category. |
| `/categories/{id}` | PUT | Update an existing category by its ID. |
| `/categories/{id}` | DELETE | Delete a category by its ID. |
| `/orders` | GET | Retrieve a list of all orders. |
| `/orders/{id}` | GET | Retrieve a specific order by its ID. |
| `/orders` | POST | Create a new order. |
| `/orders/{id}` | PUT | Update an existing order (order items) by its ID. |
| `/orders/{id}` | DELETE | Delete an order by its ID. |
| `/products` | GET | Retrieve a list of all products. |
| `/products/{id}` | GET | Retrieve a specific product by its ID. |
| `/products?categoryId={categoryId}`| GET | Retrieve a list of products filtered by a specific category ID. |
| `/products` | POST | Create a new product. |
| `/products/{id}` | PUT | Update an existing product by its ID. |
| `/products/{id}` | DELETE | Delete a product by its ID. |

---

## 🗺️ Next Steps

- Check GitHub Issues
- Pick one beginner-friendly task
- Create a branch and open a Pull Request

Small contributions are welcome 🙂
