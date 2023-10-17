function validate() {
    var user1 = document.getElementById("user1");
    var user2 = document.getElementById("user2");
    var info = document.getElementById("info");

    var result = true;
    var infoResult = "";

    if(user1 == user2) {
        infoResult = "Wybierz dwóch różnych pracowników";
        result = false;
    } else if (user1 == null || user2 == null) {
        infoResult = "Wybierz drugiego pracownika";
        result = false;
    } else if (user1.lab = user2.lab) {
        infoResult = "Wybierz pracowników z różnych pracowni";
        result = false;
    }

    info.innerHTML = infoResult;
    return result;
}