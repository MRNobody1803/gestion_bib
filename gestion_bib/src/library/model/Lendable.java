package library.model;

// Interface pour les objets pouvant être empruntés
public interface Lendable {
    void borrow();
    void returnItem();
    boolean isBorrowed();  // Ajouter cette méthode
    String getDisplayInfo(); // Ajouter pour l'affichage
}