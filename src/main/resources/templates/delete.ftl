<#import "/spring.ftl" as spring />
<#import "/formular.ftl" as formular />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Delete record</title>
    </head>
    <body>
        <h1>Delete record</h1>

        <a href="<@spring.url '/'/>">back to record list</a>

        <form action="<@spring.url relativeUrl='/{id}/delete' id=entity.id/>" method="post">
            <button type="submit">Delete</button>
        </form>
    </body>
</html>
