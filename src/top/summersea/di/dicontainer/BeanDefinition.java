package top.summersea.di.dicontainer;


import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
    private String id;
    private String className;
    private List<ConstructorArg> constructorArgList = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public List<ConstructorArg> getConstructorArgList() {
        return constructorArgList;
    }

    public void addConstructorArgList(ConstructorArg constructorArg) {
        this.constructorArgList.add(constructorArg);
    }

    public Scope getScope() {
        return scope;
    }

    public boolean isSingleton() {
        return Scope.SINGLETON.equals(scope);
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public static enum Scope {
        SINGLETON,
        PROTOTYPE;

        public static Scope getInstance(String instanceName) {
            if ("SINGLETON".equalsIgnoreCase(instanceName)) {
                return SINGLETON;
            } else if ("PROTOTYPE".equalsIgnoreCase(instanceName)) {
                return PROTOTYPE;
            }
            throw new IllegalArgumentException();
        }
    }

    public static class ConstructorArg {
        private boolean isRef;
        private Class type;
        private Object arg;

        public boolean isRef() {
            return isRef;
        }

        public void setRef(boolean ref) {
            isRef = ref;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getArg() {
            return arg;
        }

        public void setArg(Object arg) {
            this.arg = arg;
        }
    }
}


