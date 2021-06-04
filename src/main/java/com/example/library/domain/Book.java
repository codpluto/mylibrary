package com.example.library.domain;


import lombok.Data;

@Data
public class Book {
    private String isbn;
    private String bookName;
    private String coverUrl;
    private String notes;
    private String lender;
    private boolean isLentOut;
    private String buyFrom;
    private String buyDate;
    private double price;
    private String author;
    private String translator;
    private String press;
    private String publicationDate;
    private int totalPages;
    private int readProgress;
    private String contentIntroduction;
    private String authorIntroduction;
    private Integer user_id;
    private Integer shelf_id;
    private String shelfName;


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public boolean isLentOut() {
        return isLentOut;
    }

    public void setLentOut(boolean lentOut) {
        isLentOut = lentOut;
    }

    public String getBuyFrom() {
        return buyFrom;
    }

    public void setBuyFrom(String buyFrom) {
        this.buyFrom = buyFrom;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getReadProgress() {
        return readProgress;
    }

    public void setReadProgress(int readProgress) {
        this.readProgress = readProgress;
    }

    public String getContentIntroduction() {
        return contentIntroduction;
    }

    public void setContentIntroduction(String contentIntroduction) {
        this.contentIntroduction = contentIntroduction;
    }

    public String getAuthorIntroduction() {
        return authorIntroduction;
    }

    public void setAuthorIntroduction(String authorIntroduction) {
        this.authorIntroduction = authorIntroduction;
    }


//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    public int getShelf_id() {
//        return shelf_id;
//    }
//
//    public void setShelf_id(int shelf_id) {
//        this.shelf_id = shelf_id;
//    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getShelf_id() {
        return shelf_id;
    }

    public void setShelf_id(Integer shelf_id) {
        this.shelf_id = shelf_id;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    //    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//    public Book(){
//        this.shelf_id = null;
//    }
//    public Book(){
//        isbn=null;
//        bookName=null;
//        coverUrl=null;
//        bookShelf=null;
//        notes=null;
//        lender=null;
//        isLentOut=false;
//        buyFrom=null;
//        buyDate=null;
//        price=0;
//
//    }
}
