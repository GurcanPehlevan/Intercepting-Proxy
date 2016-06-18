package ProxyServer.Model;

import java.util.List;
import java.util.Map;


public class Config {

    private Map<String,String> add;
    private List<EditSub> edit;
    private List<String> delete;

    public List<EditSub> getEdit() {
        return edit;
    }

    public void setEdit(List<EditSub> edit) {
        this.edit = edit;
    }

    public Map<String, String> getAdd() {
        return add;
    }


    public void setAdd(Map<String, String> add) {
        this.add = add;
    }


    public List<String> getDelete() {
        return delete;
    }

    public void setDelete(List<String> delete) {
        this.delete = delete;
    }
}
