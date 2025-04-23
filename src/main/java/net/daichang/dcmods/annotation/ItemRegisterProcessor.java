package net.daichang.dcmods.annotation;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.crafts.DCItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;


@SupportedAnnotationTypes("net.daichang.dcmods.annotation.AutoItemRegister")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ItemRegisterProcessor extends AbstractProcessor {
    public static List<String> item_list = new ArrayList<>();

    public static Class<? extends Item> clazz = DCItems.class;

    public static final DeferredRegister<Item> deferred = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);
    public static Map<String, RegistryObject<Item>> addonItems = createItem();

    public static Map<String, RegistryObject<Item>> createItem(){
        Map<String, RegistryObject<Item>> items = new LinkedHashMap<>();
        for (String itemName : item_list){
            items.put(itemName, registryItem(itemName + "_addon"));
        }
        return items;
    }
    private static RegistryObject<Item> registryItem(String name){
        Item.Properties properties = new Item.Properties();
        return createItems(name, properties);
    }

    private static RegistryObject<Item> createItems(String name, Item.Properties properties){
        return deferred.register(name, DCItems::new);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element instanceof TypeElement typeElement) {
                    AutoItemRegister register = typeElement.getAnnotation(AutoItemRegister.class);
                    String s = register.registerId();
                    item_list.add(s);
                    System.out.println("[DC Minecraft MODS]Auto Register Item " + s);
                    System.out.println(typeElement.getQualifiedName().toString());
                }
            }
        }
        return true;
    }
}
