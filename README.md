# Fawry E-Commerce Checkout System

This is a simple Java-based simulation of an e-commerce checkout system, designed as part of the **Fawry Quantum Internship Challenge**.

##  Features

- **Product Management**: Each product includes details like name, price, quantity, weight, shipping requirements, perishability, and expiration.
- **Cart System**: Customers can add products to their cart with validation for stock availability.
- **Shipping Logic**:
  - Products that require shipping are collected and sent to `ShippingService`.
  - Shipping is calculated based on actual weight (10 EGP per kilogram).
  - Products are passed as objects implementing a `Shippable` interface (`getName()` and `getWeight()`).
- **Checkout Process**:
  - Verifies product availability and expiration.
  - Deducts total cost from customer’s balance.
  - Provides a detailed receipt and shipping notice.
- **Interface Usage**: Implements a `Shippable` interface to decouple the shipping logic from product structure, following object-oriented principles.


##  Example Output
** Checkout receipt **
4x Cheese   400
1x Biscuits   150
1x ScratchCard   50
2x TV   10000
------------------------
Subtotal   10600
Shipping   215
Amount     10815
Customer balance after payment: 19185


##  Technologies
- Java
- OOP (Object-Oriented Programming)
- Interfaces & Abstraction

##  File
- `FawryECommerceSystem.java` – Full implementation in one file.
