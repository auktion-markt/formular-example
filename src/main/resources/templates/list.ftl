<#import "/spring.ftl" as spring />
<#import "/formular.ftl" as formular />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>List records</title>
    </head>
    <body>
        <h1>List record</h1>

        <a href="<@spring.url '/create'/>">create</a>

        <table border="1">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Years</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <#list data.content as row>
                    <tr>
                        <td>${row.id}</td>
                        <td>${row.exampleString!'<i>not set</i>'}</td>
                        <td>${row.exampleNumber!'<i>not set</i>'}</td>
                        <td>
                            <a href="<@spring.url relativeUrl='/{id}' id=row.id/>">edit</a>
                            <a href="<@spring.url relativeUrl='/{id}/delete' id=row.id/>">delete</a>
                        </td>
                    </tr>
                </#list>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4">
                        ${data.number * data.size + 1} to ${(data.number + 1) * data.size} of ${data.totalElements}
                        (page ${data.number + 1} of ${data.totalPages})
                        <#if !data.first><a href="<@spring.url relativeUrl='/?page={page}' page=data.number - 1/>">prev</a><#else>prev</#if>
                        <#if !data.last><a href="<@spring.url relativeUrl='/?page={page}' page=data.number + 1/>">next</a><#else>next</#if>
                    </td>
                </tr>
            </tfoot>
        </table>
    </body>
</html>
