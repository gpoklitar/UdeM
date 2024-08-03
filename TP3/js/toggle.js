$(document).ready(TraitementChargementPage);

function TraitementChargementPage() {
  $("section").parent().find("div:last-child").hide();
  $(".mdl-card__media , .mdl-card__title").click(toggleContent);
}

function toggleContent(event) {
  $(this).parent().find("div:last-child").toggle();
}