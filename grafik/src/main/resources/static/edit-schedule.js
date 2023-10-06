function validate() {
    var user1 = document.getElementById("user1");
    var user2 = document.getElementById("user2");
    var info = document.getElementById("info");

    var result = true;
    var infoResult = "";

    if(user1 = user2) {
            infoResult = "Wybierz dwóch różnych pracowników <br>";
            result = false;
    } else if (user1.lab = user2.lab){
            infoResult = "Wybierz pracowników z różnych pracowni <br>";
            result = false;
    }
    info.innerHTML = infoResult;
    return result;
}
