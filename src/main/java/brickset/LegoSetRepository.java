package brickset;

import repository.Repository;

import java.util.Comparator;


/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /**
     * Returns the number of LEGO sets with the tag specified.
     *
     * @param tag a LEGO set tag
     * @return the number of LEGO sets with the tag specified
     */
    public long countLegoSetsWithTag(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().contains(tag))
                .count();
    }

    /**
     * Kiírja azoknak a lego setteknek a nevét, amelyek a megadott témába tartoznak
     */
    public void printSetsWithTheme(String theme){
        getAll().stream()
                .filter(legoSet -> legoSet.getTheme().equalsIgnoreCase(theme))
                .map(LegoSet::getName)
                .forEach(System.out::println);
    }

    /**
     * Kiírja a legtöbb darabból álló n lego set nevét
     */
    public void printSetsWithMostPieces(int n){
        getAll().stream()
                .sorted(Comparator.comparingInt(LegoSet::getPieces).reversed())
                .limit(n)
                .map(LegoSet::getName)
                .forEach(System.out::println);
    }

    /**
     * Visszaadja hogy hány darab lego set van a megadott csomagolásból
     */
    public long countSetsWithPackagingType(String p){
        return getAll().stream()
                .filter(legoSet -> legoSet.getPackagingType() == PackagingType.valueOf(p.toUpperCase()))
                .map(LegoSet::getNumber)
                .count();
    }

    /**
     * Kiírja azoknak a lego seteknek a nevét, amelyek a megadott darabszámok közé esnek
     */
    public void printSetsBetweenGivenPieces(int n, int m){
        getAll().stream()
                .filter(legoSet -> legoSet.getPieces() > n && legoSet.getPieces() < m)
                .map(LegoSet::getName)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Kiírja azoknak a lego seteknek a nevét, amelyek a megadott súlynál nehezebbek
     */
    public void printSetsNameHigherThanGivenWeight(int w){
        getAll().stream()
                .filter(legoSet -> legoSet.getDimensions() != null && legoSet.getDimensions().getWeight() != null && legoSet.getDimensions().getWeight() >= w)
                .map(LegoSet::getName)
                .sorted()
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();
        System.out.println(repository.countLegoSetsWithTag("Microscale"));

        repository.printSetsWithTheme("Star Wars");
        repository.printSetsWithMostPieces(2);
        System.out.println(repository.countSetsWithPackagingType("polybag"));
        repository.printSetsBetweenGivenPieces(1000, 2000);
        repository.printSetsNameHigherThanGivenWeight(2);
    }

}
