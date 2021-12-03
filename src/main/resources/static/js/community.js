/**
 * 提交回复
 */
function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    commentToTarget(questionId, 1, content);
}

function commentToTarget(targetId, type, content) {
    if (!content) {
        alert("输入内容不能为空!");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                location.reload();
            } else {
                if (response.code == 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=ceb23fb5d2e932187320&redirect_uri=http://localhost:8081/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                }
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    commentToTarget(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    // 获取二级评论展开状态
    let collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        let subCommentContainer = $("#comment-"+id);

        if (subCommentContainer.children().length !== 1) {
            // 展开
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {

                    let mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": "来自 "+comment.user.name + " 的回复:"
                    })).append($("<span/>", {
                        "html": comment.content
                    })).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:MM')
                    }));

                    let mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaBodyElement);

                    let commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                // 展开
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}