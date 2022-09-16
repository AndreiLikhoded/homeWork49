<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Candidates</title>
</head>
<body>
   <#list candidate as candidate>
       <h2>${candidate.id}</h2>
       <p>${candidate.name}</p>
       <p> test <a href="/votes"> Choose a candidate </a></p>
   </#list>
</body>
</html>