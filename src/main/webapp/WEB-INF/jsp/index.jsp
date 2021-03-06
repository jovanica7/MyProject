<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Address Book</title>
    <link rel="stylesheet" href="/assets/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
<body>
<div class="container-fluid">
<div class="row">
  <h1 class="col-md-10 col-md-offset-4 vcenter text-info">My contacts</h1>
 </div>
 <div class="row"> <div class="col-md-6 btn-toolbar"><button class="btn btn-warning" onclick="location.href='/myAddressBook/addNew'">+ New contact</button><button class="btn btn-warning">Export all contacts</button></div>
		<div class="col-md-14 pull-right">
		<div class="col-md-10">
            <div class="input-group text-right" id="adv-search"><c:url var="searchUrl" value="/myAddressBook/searchByName"/> 
              <form class="form-horizontal" action="${searchUrl}" method="post">
            	<div class="form-group">
                 <input type="text" class="form-control" name= "firstName" th:value="${contact.firstName}" placeholder="Search for contact by name" />
                 <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                 </div>
                </form>
            </div>
            </div>
          </div>
        </div>
  <div class="table-responsive">
   <table id="allcontacts" class="table table-hover">
    <thead>
      <tr>
      	<th class="hidden-md hidden-lg">Id</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Date Of Birth</th>
        <th>Address</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Sex</th>
        <th></th>
      </tr>
    </thead>
    <c:forEach var="contact" items="${allContacts}">
                <tr>
                	<td class="hidden-md  hidden-lg"><c:out value="${contact.id}"/></td>
                    <td><c:out value="${contact.firstName}" /></td>
                    <td><c:out value="${contact.lastName}" /></td>
                    <td><c:out value="${contact.dateOfBirth}"/></td>
                    <td><c:out value="${contact.address}"/></td>
                    <td><c:out value="${contact.phone}" /></td>
                    <td><c:out value="${contact.email}" /></td> 
                    <td><c:out value="${contact.sex}"/></td>
                    <td>
                    <div class="dropdown">
   					 <button class="btn btn-success dropdown-toggle" type="button" data-toggle="dropdown">Actions
   					 <span class="caret"></span></button>
   					 <ul class="dropdown-menu">
					<li>
           			 <button class="btn btn-warning" onclick="location.href='/myAddressBook/update/${contact.id}'" >Update contact</button>
       				</li>     				   
       				 <li><c:url var="deleteUrl" value="/myAddressBook/delete"/>  
     				   <form action="${deleteUrl}" method="post">
                    <input name="id" type="hidden" value="${contact.id}"/>
           			 <button type="submit" onClick="return confirm('Are you sure you want to delete this contact?')" class="btn btn-warning">Delete contact</button>
       				 </form></li>
       				 <li><c:url var="detailsUrl" value="/myAddressBook/details"/>  
     				   <form action="${detailsUrl}" method="post">
                    <input name="id" type="hidden" value="${contact.id}"/>
           			 <button class="btn btn-warning" onclick="location.href='/myAddressBook/details/${contact.id}'" >Details</button>
       				</form></li> 
   					 </ul>
 					 </div>
 					</td>           
                </tr>
            </c:forEach>
    <tbody>
</tbody>
</table>
</div>
 </div>
</body>
</html>
