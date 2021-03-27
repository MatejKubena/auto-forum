package com.example.autoforum.favorite;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;



public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getAllPosts() {
        return new ArrayList<>(favoriteRepository.findAll());
    }

    public Favorite getPost(int id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    public void deleteFavorite(int id) { favoriteRepository.deleteById(id);
    }
}
