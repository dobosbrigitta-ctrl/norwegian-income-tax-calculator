# Norwegian Income Tax Calculator

A simple and intuitive Norwegian tax calculator that helps you estimate your take-home pay. 
Just enter your gross salary, and the app instantly calculates your net income based on Norwegian tax regulations.

## Technological background
* Java/25
* Spring Boot
* Maven

 
 ## How is it used?
The application runs locally on **port 8080** (http://localhost:8080).

From a user perspective, it works in a few simple steps:
1. **Enter your gross salary** data into the input field.
2. **Select your preferred currency** from the dropdown menu.
3. The app fetches live exchange rates from the [ExchangeRate-API](https://exchangerate-api.com) to perform accurate conversions.
4. **Get your net income** instantly calculated on the screen.


## Acknowledgments

* **ExchangeRate-API**: The live currency exchange rates are fetched from [ExchangeRate-API](https://exchangerate-api.com).
* **Everything else**: All other core features, calculation logic, and code were designed and developed entirely from scratch.


    
