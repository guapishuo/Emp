<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<input type="button" onclick="goTest()" value="提交">


<script type="text/javascript" src="js/jquery-1.11.3.js" charset="utf-8"></script>
<script>

    // function goTest(){
    //     $.ajax({
    //         url:"/test",
    //         method:"post",
    //         success:function(data){
    //             console.log(data);
    //         }
    //     })
    // }
    window.onload=function () {
        alert("2");
        $.ajax({
            url: "/test",
            method: "post",
            success: function (data) {
                console.log(data);
            }
        })
    };

</script>

</body>
</html>
