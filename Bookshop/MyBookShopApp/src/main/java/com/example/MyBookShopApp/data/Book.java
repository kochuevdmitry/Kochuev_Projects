package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.file.BookFile;
import com.example.MyBookShopApp.data.book.links.Book2UserViewHistoryEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@ApiModel(description = "entity representing a book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id generated by db automaticaly")
    private Integer id;

    @Column(name = "pub_date")
    @ApiModelProperty("date of book publication")
    private Date pubDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private Authors author;

    @Column(name = "is_bestseller")
    @ApiModelProperty("if isBestseller = 1 so the book is considered to be bestseller and  if 0 the book is not a " +
            "bestseller")
    private Integer isBestseller;

    @ApiModelProperty("mnemonical identity sequence of characters")
    private String slug;
    @ApiModelProperty("book title")
    private String title;
    @ApiModelProperty("image url")
    private String image;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty("book description text")
    private String description;

    @Column(name = "price")
    @JsonProperty("price")
    @ApiModelProperty("book price without discount")
    private Integer priceOld;

    @Column(name = "discount")
    @JsonProperty("discount")
    @ApiModelProperty("discount value for book")
    private Double price;

    @ManyToMany
    @JoinTable(name = "book2tag",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    @JsonIgnore
    private List<Tags> tagsList;

    @ManyToMany
    @JoinTable(name = "book2genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    //@JoinColumn(name = "genre_id", referencedColumnName = "id")
    @JsonIgnore
    private List<Genre> genre;

    @OneToMany(mappedBy = "book")
    private List<BookFile> bookFileList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Book2UserViewHistoryEntity> book2UserViewHistoryEntityList = new ArrayList<>();

    @Column(name = "popularity")
    private Double popularity;

    @Column(name = "rating")
    private Double rating;

    @OneToMany(mappedBy = "book")
    private List<BookReviewLikeEntity> bookReviewLikeEntities = new ArrayList<>();

    public List<BookReviewLikeEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikeEntities(List<BookReviewLikeEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public List<Book2UserViewHistoryEntity> getBook2UserViewHistoryEntityList() {
        return book2UserViewHistoryEntityList;
    }

    public void setBook2UserViewHistoryEntityList(List<Book2UserViewHistoryEntity> book2UserViewHistoryEntityList) {
        this.book2UserViewHistoryEntityList = book2UserViewHistoryEntityList;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public List<BookFile> getBookFileList() {
        return bookFileList;
    }

    public void setBookFileList(List<BookFile> bookFileList) {
        this.bookFileList = bookFileList;
    }

    @JsonProperty//чтобы параметр появился в javascript
    public Integer discountPrice() {
        Integer discountedPriceInt = priceOld - Math.toIntExact(Math.round(price * priceOld));
        return discountedPriceInt;
    }

    @JsonProperty//чтобы параметр появился в javascript
    public Integer discount() {
        Integer discount = Math.toIntExact(Math.round(price * 100));
        return discount;
    }

    @JsonGetter("author")
    public String authorsFullName() {
        return author.toString();
    }



    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(Integer isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Authors getAuthor() {
        return author;
    }

    public void setAuthor(Authors author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", pubDate=" + pubDate +
                ", author=" + author +
                ", tag=" + tagsList +
                ", isBestseller=" + isBestseller +
                ", slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", priceOld=" + priceOld +
                ", price=" + price +
                '}';
    }
}
