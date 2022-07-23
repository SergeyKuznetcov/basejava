/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
            size = 0;
        }
    }

    void save(Resume r) {
        storage[size]=r;
        size++;
    }

    Resume get(String uuid) {

        if (size==0){
            return null;
        }else {
            Resume res = null;
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)){
                    res = storage[i];
                    break;
                }
            }
            return res;
        }
    }

    void delete(String uuid) {
        Resume[] res = new Resume[10000];
        int temp = 0;
        boolean isExist = false;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)){
                temp = i;
                isExist = true;
                break;
            }
        }
        if (isExist){
            for (int i = temp; i < size; i++) {
                storage[i] = storage[i+1];
            }
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (size==0){
            return new Resume[0];
        }else {
            Resume[] res = new Resume[size];
            for (int i = 0; i < size; i++) {
                res[i] = storage[i];
            }
            return res;
        }
    }

    int size() {
        return size;
    }
}
