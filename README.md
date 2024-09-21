# e-construction

Overview

E-construction is a comprehensive system designed to bridge the gap between clients seeking reliable construction professionals and craftsmen looking for job opportunities. The project consists of three main services, providing a seamless experience for all users involved.

System Components

    User Service:
        User registration and authentication, including token generation for secure access.
        Profile management and user role enforcement.

    Master Finder Service:
        Job posting capabilities for clients.
        A searchable directory of craftsmen.
        Contract negotiation features.
        Review and rating system for services rendered.

    Notification Service:
        Manages various types of notifications, including:
            Activation emails.
            Password reset emails.
            Alerts for job offers to clients and responses from craftsmen.
        All notifications are sent asynchronously via a message broker.

Technologies Used

    API Gateway: Routes user requests to the appropriate services.
    Eureka: Facilitates service discovery.
    Backend: Developed using the Spring Boot framework.
    Frontend: Built with Vue.js.