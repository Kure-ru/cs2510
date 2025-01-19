package lectures;

import tester.Tester;

interface IDocument{
    String getSource();
    String getAuthor();
    String getTitle();
    ILoDocuments sortByAuthor();
    boolean authorBefore(IDocument that);
    ILoDocuments filterDuplicates();
}

interface ILoDocuments{
    String getSource();
    ILoDocuments sortByAuthor();
    ILoDocuments insert(IDocument document);
    ILoDocuments filterDuplicates();
    ILoDocuments filterDuplicatesHelper();
    boolean contains(IDocument document);
}

class WikiArticle implements IDocument {
    String author;
    String title;
    ILoDocuments bibliography;
    String url;

    WikiArticle(String author, String title, ILoDocuments bibliography, String url){
        this.author = author;
        this.title = title;
        this.bibliography = bibliography;
        this.url = url;
    }

    public String getSource() {
        return this.bibliography.getSource();
    }

    public String getTitle(){
        return "";
    }

    public String getAuthor(){
        return "";
    }

    public ILoDocuments sortByAuthor() {
        return this.bibliography.sortByAuthor();
    }

    public boolean authorBefore(IDocument that) {
        return this.author.compareTo(that.getAuthor()) < 0;
    }

    public ILoDocuments filterDuplicates() {
        return this.bibliography.filterDuplicates();
    }
}

class Book8 implements IDocument {
    String author;
    String title;
    ILoDocuments bibliography;
    String publisher;

    Book8(String author, String title, ILoDocuments bibliography, String publisher){
        this.author = author;
        this.title = title;
        this.bibliography = bibliography;
        this.publisher = publisher;
    }

    public String getSource() {
        return this.bibliography.getSource();
    }

    public String getTitle(){
        return "\"" + this.title + "\".";
    }


    public String getAuthor(){
        return this.author;
    }

    public ILoDocuments sortByAuthor() {
        return this.bibliography.sortByAuthor();
    }

    public boolean authorBefore(IDocument that) {
        return this.author.compareTo(that.getAuthor()) < 0;
    }

    public ILoDocuments filterDuplicates() {
        return this.bibliography.filterDuplicates();
    }
}

class MtDocument implements ILoDocuments {
    MtDocument(){}

    public String getSource() {
        return "";
    }

    public ILoDocuments sortByAuthor() {
        return this;
    }

    public ILoDocuments insert (IDocument document){
        return new ConsLoDocument(document, this);
    }

    public ILoDocuments filterDuplicates() {
        return this;
    }

    public ILoDocuments filterDuplicatesHelper() {
        return this;
    }

    public boolean contains(IDocument document) {
        return false;
    }
}

class ConsLoDocument implements ILoDocuments {
    IDocument first;
    ILoDocuments rest;

    ConsLoDocument(IDocument first, ILoDocuments rest){
        this.first = first;
        this.rest = rest;
    }

    public String getSource() {
        if (this.first.getAuthor().isEmpty()){
            return "";
        } else if (this.rest instanceof MtDocument){
            return this.first.getAuthor() + ". " + this.first.getTitle();
        } else
        {
            return this.first.getAuthor() + ". " + this.first.getTitle() + "\n" + this.rest.getSource();
        }
    }

    public ILoDocuments sortByAuthor() {
        return this.rest.sortByAuthor().insert(this.first);
    }

    public ILoDocuments insert(IDocument document) {
        if (document.authorBefore(this.first)) {
            return new ConsLoDocument(document, this);
        } else {
            return new ConsLoDocument(this.first, this.rest.insert(document));
        }
    }

    public ILoDocuments filterDuplicates() {
        return this.sortByAuthor().filterDuplicatesHelper();
    }

    public ILoDocuments filterDuplicatesHelper() {
        if (this.rest.contains(this.first)) {
            return this.rest.filterDuplicatesHelper();
        } else {
            return new ConsLoDocument(this.first, this.rest.filterDuplicatesHelper());
        }
    }

    public boolean contains(IDocument document) {
        if (this.first.getAuthor().equals(document.getAuthor()) && this.first.getTitle().equals(document.getTitle())){
            return true;
        } else {
            return this.rest.contains(document);
        }
    }
}

class ExamplesDocuments {
    ILoDocuments mt = new MtDocument();

    IDocument htdp = new Book8("Felleisen, Matthias", "How to Design Programs", mt, "MIT Press");
    IDocument sicp = new Book8("Abelson, Harold", "Structure and Interpretation of Computer Programs", new ConsLoDocument(htdp, mt), "MIT Press");
    IDocument wiki = new WikiArticle("Preston-Kendal, Daphne", "How to Design Programs", new ConsLoDocument(sicp, new ConsLoDocument(htdp, mt)), "https://en.wikipedia.org/wiki/How_to_Design_Programs");
    IDocument wiki2 = new WikiArticle("Preston-Kendal, Daphne", "Structure and Interpretation of Computer Programs", new ConsLoDocument(sicp, mt), "https://en.wikipedia.org/wiki/Structure_and_Interpretation_of_Computer_Programs");
    IDocument java = new Book8("Eckel, Bruce", "Thinking in Java", new ConsLoDocument(wiki, new ConsLoDocument(wiki2, mt)), "Prentice Hall");
    IDocument pragmatic = new Book8("Hunt, Andrew", "The Pragmatic Programmer", new ConsLoDocument(wiki2, new ConsLoDocument(sicp, new ConsLoDocument(java, mt))), "Addison-Wesley");
    IDocument cleanCode = new Book8("Martin, Robert", "Clean Code", new ConsLoDocument(java, new ConsLoDocument(java, mt)), "Prentice Hall");

    boolean testGetSources(Tester t) {
        return t.checkExpect(this.java.getSource(), "") &&
                t.checkExpect(this.sicp.getSource(), "Felleisen, Matthias. \"How to Design Programs\".") &&
                t.checkExpect(this.wiki.getSource(), "Abelson, Harold. \"Structure and Interpretation of Computer Programs\".\nFelleisen, Matthias. \"How to Design Programs\".") &&
                t.checkExpect(this.wiki2.getSource(), "Abelson, Harold. \"Structure and Interpretation of Computer Programs\".");
    }

    boolean testSortByAuthor(Tester t) {
        return t.checkExpect(this.java.sortByAuthor(), new ConsLoDocument(wiki2, new ConsLoDocument(wiki, mt))) &&
                t.checkExpect(this.sicp.sortByAuthor(), new ConsLoDocument(htdp, mt))  &&
                t.checkExpect(this.wiki.sortByAuthor(), new ConsLoDocument(sicp, new ConsLoDocument(htdp, mt))) &&
                t.checkExpect(this.wiki2.sortByAuthor(),  new ConsLoDocument(sicp, mt)) &&
                t.checkExpect(this.pragmatic.sortByAuthor(), new ConsLoDocument(sicp, new ConsLoDocument(java, new ConsLoDocument(wiki2, mt))));
    }

    boolean testFilterDuplicates(Tester t){
        return t.checkExpect(this.cleanCode.filterDuplicates(), new ConsLoDocument(java, mt));
    }
}