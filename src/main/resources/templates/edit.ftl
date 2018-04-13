<#import "/spring.ftl" as spring />
<#import "/formular.ftl" as formular />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Create/Edit record</title>
    </head>
    <body>
        <h1>Create or update record</h1>

        <p><a href="<@spring.url '/'/>">back to record list</a></p>

        <p>ID: ${(entity.id)!'not assigned yet'}</p>

        <@formular.render_form form_specification form_state/>
    </body>
</html>
