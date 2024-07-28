package mk.ukim.nosql.model;

import com.basho.riak.client.api.commands.kv.UpdateValue;

public class CaseUpdate extends UpdateValue.Update<Case> {

    private final Case update;

    public CaseUpdate(Case update) {
        this.update = update;
    }

    @Override
    public Case apply(Case aCase) {
        if (aCase == null) {
            aCase = new Case();
        }

        aCase.setId_s(update.getId_s());
        aCase.setCity_s(update.getCity_s());
        aCase.setProvince_s(update.getProvince_s());
        aCase.setInfectionCase_s(update.getInfectionCase_s());
        aCase.setLatitude_d(update.getLatitude_d());
        aCase.setLongitude_d(update.getLongitude_d());
        aCase.setConfirmed_i(update.getConfirmed_i());
        aCase.setGroup_b(update.getGroup_b());

        return aCase;
    }
}
