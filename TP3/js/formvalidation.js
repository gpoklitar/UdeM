$(document).ready(TraitementChargementPage);

function TraitementChargementPage() {
  $("#submitBtn").click(checkEmails);
}

function checkEmails(event) {
  if ($("#courriel").val() !== $("#confcourriel").val()) {
    document
      .getElementById("confcourriel")
      .setCustomValidity(
        "Les deux adresses de courriel doivent etre identiques."
      );
  } else {
    document.getElementById("confcourriel").setCustomValidity("");
  }
}