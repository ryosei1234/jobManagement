/**
 * 受験報告新規登録機能用のJavaScriptファイルです
 */
function check(){
	if (document.exam_form["application_route"][6].checked ) {
        document.exam_form["other"] . disabled = false;
    } else {
        document.exam_form["other"] . disabled = true;
    }
}
window.onload = changeDisabled;

}