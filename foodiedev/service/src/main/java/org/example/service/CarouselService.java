package org.example.service;

import org.example.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    public List<Carousel> queryAll(Integer isShow);

}
