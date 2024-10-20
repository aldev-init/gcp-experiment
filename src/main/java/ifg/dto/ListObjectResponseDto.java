package ifg.dto;

public class ListObjectResponseDto {

    public ListObjectResponseDto(String id, String name){
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }
}
