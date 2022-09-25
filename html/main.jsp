<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Photogallery Main Page</title>
</head>
<body>
    <head><title></title></head>
    <body>
        <h1 style="text-align: center;">
            Logged in as: <% out.print(session.getAttribute("USER_ID")); %>
        </h1>
        <div style="text-align: center;">
            <form action="/photogallery/upload" method="GET">
                <input type="submit" value="UPLOAD">
            </form>
        </div>
        <br><br>
        <div style="text-align: center;">
            <form action="/photogallery/gallery" method="GET">
                <input type="submit" value="GALLERY">
            </form>
        </div>
        <br><br>
        <div style="text-align: center;">
            <form action="/photogallery/search" method="GET">
                <input type="submit" value="SEARCH">
            </form>
        </div>
        <br><br>
        <div style="text-align: center;">
            <form action="/photogallery/logout" method="GET">
                <input type="submit" value="LOGOUT">
            </form>
        </div>

    </body>

</body>
</html>