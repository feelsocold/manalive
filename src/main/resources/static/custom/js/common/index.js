// Get the modal
var modal = document.getElementById('Login-Modal');

// Get the button that opens the modal
var btn = document.getElementById("Login-Btn");

var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
btn.onclick = function(e) {
    e.preventDefault();
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
    document.getElementById("Login-Email").value = "";
    document.getElementById("Login-Password").value = "";

}

// When the user clicks anywhere outside of the modal, close it
// Get the <span> element that closes the modal
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        document.getElementById("Login-Email").value = "";
        document.getElementById("Login-Password").value = "";
    }
}
