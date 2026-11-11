# Norwegian Income Tax Calculator

A Java Spring Boot application designed to estimate net income based on Norwegian tax rules and support financial decision-making when comparing job opportunities.

Users can enter their gross salary, select a preferred currency, and view estimated take-home pay. The application also integrates external services for live currency exchange rates and AI-powered job offer analysis.

---

### Backend Architecture

- **Java 25** – Core programming language.
- **Spring Boot** – Backend application framework.
- **Maven** – Dependency and build management.
- **REST API** – Backend endpoints and service communication.

### External API Integration

The application integrates with the **ExchangeRate-API** to retrieve live currency exchange rates.

Key integration concepts include:
- HTTP communication with external services.
- REST API consumption.
- JSON response handling.
- External service configuration.
- Currency conversion using retrieved exchange rates.

### AI Integration – Microsoft Azure AI Foundry

The application integrates a custom AI agent built with **Microsoft Azure AI Foundry** to support job offer analysis and comparison.

The AI agent analyses job-related financial information and provides contextual insights, including:

- Comparison of job offers.
- Differences in estimated monthly disposable income.
- Salary and expense trade-offs.
- Identification of missing information relevant to decision-making.
- AI-generated explanations to support informed comparisons.

A dedicated Java service handles communication with the Azure AI Foundry agent and integrates its responses into the application.

### Calculation Logic

The application's financial and tax-related calculations are handled by Java backend services. The AI agent provides additional analysis and interpretation rather than performing the underlying tax calculations.

### Key Technologies

- Java 25
- Spring Boot
- Microsoft Azure AI Foundry, using gpt-5.4-nano
- REST API
- HTTP & JSON
- ExchangeRate-API
- Maven

---

## Key Features

- **Net Income Estimation:** Calculate estimated take-home pay based on gross salary and Norwegian tax rules.
- **Currency Selection:** Choose the preferred currency for displaying financial results.
- **Live Exchange Rates:** Retrieve current exchange rates through an external API.
- **Currency Conversion:** Convert financial results into the selected currency.
- **AI-Powered Job Offer Analysis:** Use a custom Azure AI Foundry agent to analyse and compare job opportunities.
- **Financial Comparison Insights:** Understand differences in monthly disposable income and salary-related trade-offs.

---

## Software & AI Integration

This project demonstrates practical experience integrating multiple external services into a Java backend application.

It combines conventional REST API integration with AI agent integration, covering HTTP communication, JSON data exchange, external service configuration, prompt-based interaction, and AI-generated response handling.

The project explores how AI agents can extend traditional backend applications by providing contextual analysis on top of deterministic business logic.

---

## Acknowledgements

Developed as a hands-on portfolio project to strengthen practical skills in Java backend development, Spring Boot, REST API integration, external service connectivity, AI agent integration, Microsoft Azure AI Foundry, financial data processing, and software integration.

## Acknowledgments

* **ExchangeRate-API**: The live currency exchange rates are fetched from [ExchangeRate-API](https://exchangerate-api.com).
* **Everything else**: All other core features, calculation logic, and code were designed and developed entirely from scratch.


    
