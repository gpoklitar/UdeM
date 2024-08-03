
$(document).ready(TraitementChargementPage);

function TraitementChargementPage() {
  // parametres pour l'affichage de la date
  var options = { year: "numeric", month: "long", day: "numeric" },
    // obtention de la date du jour
    d = new Date(),
    // localisation de la date en franÃ§ais du Canada avec les options de l'affichage
    datefrancais = d.toLocaleDateString("fr-CA", {
      year: "numeric",
      month: "long",
      day: "numeric",
    }),
    dateAttribut = d.toLocaleDateString("fr-CA", {
      year: "numeric",
      month: "numeric",
      day: "numeric",
    }),
    // Chaine qui sera affichee
    dateAffichee = "le " + datefrancais;

  // Insérer la date dans l'élément avec ID "dateAttributElementId"
  var dateElement = $("#dateAttributElementId");
  dateElement.text("Droits d'auteur © Gueorgui Poklitar (poklitag), " + dateAffichee + ".");

  // Ajouter le contenu de la variable dateAttribut dans l'attribut "datetime" de l'élément avec ID "dateAttributElementId"
  dateElement.attr("datetime", dateAttribut);
}


