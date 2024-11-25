# library-system

<h3 align="left">I. Project Description</h3>
The Online Library System, developed using the JavaFX API, offers a role-based platform for students, librarians, and administrators. Each user has different services. Users log in with their ID and username, then the system identifies the user by checking the first two digits of the ID: 
If it starts with 11: The user is an Admin.
If it starts with 22: The user is as a Librarian.
If it starts with 33: The user is as a Student. 
Other than that an error message will appear, each user has a personalized homepage:

• Admins can manage users, oversee inventory, adjust settings, and log out. 

• Librarians can manage books, track inventory, handle loan requests, and log out.

• Students can browse, borrow, and return books, check their account details, and log out
. 
the system is backed by a Oracle SQL database to ensure secure storage of user data and book 
records, providing an efficient library experience.

<h3 align="left">II. Class Diagram</h3>
- Inheritance: The super class User has three subclasses: Student, Administrator, and Librarian.
- Associations: an association relationship between BookSearch and Student, and an association relationship between UserSearch and the Administrator
- Compositions: User has a composition relationship with Request, and Request has a composition relationship with Book.
- Implementation: BookSearch and UserSearch implement the Searchable interface.
<img width="513" alt="Screenshot 2024-11-25 210248" src="https://github.com/user-attachments/assets/85cae5ba-b338-4206-83b5-a95328c950d0">

<h3 align="left">III. System Hierarchy and Database Schema</h3>
<img width="457" alt="Screenshot 2024-11-25 210842" src="https://github.com/user-attachments/assets/642aa92e-4b71-4a87-89c1-35ff7de3c1ed">
<img width="393" alt="Screenshot 2024-11-25 211437" src="https://github.com/user-attachments/assets/39bea9f8-a9df-4e95-a2ef-9a1c323d5d3f">


