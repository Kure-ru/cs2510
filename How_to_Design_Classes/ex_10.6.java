/* Exercise 10.6
Design the method sizeString for this class. It produces one of three strings,
depending on the number of pixels in the image:
1. "small" for images with 10,000 pixels or fewer;
2. "medium" for images with between 10,001 and 1,000,000 pixels;
3. "large" for images that are even larger than that.
Remember that the number of pixels in an image is determined by the area
of the image.
 */

// represent information about an image
class Image {
    int width; // in pixels
    int height; // in pixels
    String source;

    Image(int width, int height, String source) {
        this.width = width;
        this.height = height;
        this.source = source;
    }

    String sizeString(){
        if (this.width * this.height <= 10000){
            return "small";
        } else if (this.width * this.height <= 1000000){
            return "medium";
        } else {
            return "large";
        }
    }
}
