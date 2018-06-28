package com.jonaszwiacek.siker.SpringSiker.Converters;

import com.jonaszwiacek.siker.Siker.Sorter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Set;

public class SortingConverter implements Converter<String, Sorter> {
    @Override
    public Sorter convert(String o) {
        try {
            return Sorter.valueOf(o);
        }
        catch (IllegalArgumentException ex) {
            return Sorter.NONE;
        }
    }
}
