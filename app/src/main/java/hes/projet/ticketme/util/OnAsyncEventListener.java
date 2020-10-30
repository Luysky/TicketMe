package hes.projet.ticketme.util;

/**
 * Copied from https://github.com/aislab-hevs/644-1_Course_Material
 */
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
