# library-system

I. Project Description
The Online Library System, developed using the JavaFX API, offers a role-based platform for students, librarians, and administrators. Each user has different services. Users log in with their ID and username, then the system identifies the user by checking the first two digits of the ID: 
• If it starts with 11: The user is an Admin. 
• If it starts with 22: The user is as a Librarian. 
• If it starts with 33: The user is as a Student. 
Other than that an error message will appear, each user has a personalized homepage: 
• Admins can manage users, oversee inventory, adjust settings, and log out. 
• Librarians can manage books, track inventory, handle loan requests, and log out. 
• Students can browse, borrow, and return books, check their account details, and log out. 
the system is backed by a Oracle SQL database to ensure secure storage of user data and book 
records, providing an efficient library experience.

II. Class Diagram
• Inheritance: The super class User has three subclasses: Student, Administrator, and Librarian. 
• Associations: an association relationship between BookSearch and Student, and an association relationship between UserSearch and the Administrator. 
• Compositions: User has a composition relationship with Request, and Request has a composition relationship with Book. 
• Implementation: BookSearch and UserSearch implement the Searchable interface.